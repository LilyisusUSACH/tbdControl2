import { type } from '../.nuxt/types/imports';

<script>
export default {
  data() {
    return {
      tasks: [], // Aquí irían las tareas
      search: '',
      filter: 'Todas',
      selectedTask: null, // Para editar tareas
      taskDialog: false, // Controlar la visibilidad del diálogo de tarea
    };
  },
  computed: {
    filteredTasks() {
      return this.tasks.filter(task => {
        const searchText = this.search.toLowerCase();
        return (
            (task.title.toLowerCase().includes(searchText) || task.description.toLowerCase().includes(searchText)) &&
            (this.filter === 'Todas' ||
                (this.filter === 'Pendientes' && !task.completed) ||
                (this.filter === 'Completadas' && task.completed))
        );
      });
    }
  },
  methods: {
    openTaskDialog(task) {
      this.selectedTask = task ? Object.assign({}, task) : { title: '', description: '', dueDate: new Date().toISOString().substr(0, 10), completed: false };
      this.taskDialog = true;
    },
    saveTask() {
      if (this.selectedTask.id) {
        // Actualizar tarea existente
        const index = this.tasks.findIndex(task => task.id === this.selectedTask.id);
        if (index !== -1) {
          this.tasks.splice(index, 1, this.selectedTask);
          // Enviar solicitud PUT al servidor para actualizar la tarea
        }
      } else {
        // Crear nueva tarea
        this.selectedTask.id = Date.now(); // Simulación de ID; remover para producción
        this.tasks.push(this.selectedTask);
        // Enviar solicitud POST al servidor para crear la tarea
      }
      this.taskDialog = false;
    },
    completeTask(task) {
      const index = this.tasks.indexOf(task);
      if (index !== -1) {
        this.tasks[index].completed = true;
        // Enviar solicitud PUT al servidor para marcar la tarea como completada
      }
    },
    deleteTask(task) {
      const index = this.tasks.indexOf(task);
      if (index !== -1) {
        this.tasks.splice(index, 1);
        // Enviar solicitud DELETE al servidor para eliminar la tarea
      }
    },
  },
};

</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="10" md="8" lg="6">
        <v-card class="elevation-12" color="surface-variant">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>Gestión de Tareas</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <!-- Controles de búsqueda y filtros -->
            <v-row>
              <v-col cols="12" sm="6">
                <v-text-field
                    v-model="search"
                    label="Buscar tareas"
                    prepend-inner-icon="mdi-magnify"
                    outlined
                    clearable
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-select
                    v-model="filter"
                    :items="['Todas', 'Pendientes', 'Completadas']"
                    label="Filtrar por"
                    outlined
                ></v-select>
              </v-col>
            </v-row>

            <!-- Lista de tareas -->
            <v-list dense>
              <v-list-item-group>
                <v-list-item v-for="task in filteredTasks" :key="task.id">
                  <v-list-item-content>
                    <v-list-item-title :class="{ 'text-decoration-line-through': task.completed }">
                      {{ task.title }}
                    </v-list-item-title>
                    <v-list-item-subtitle>{{ task.description }}</v-list-item-subtitle>
                  </v-list-item-content>
                  <v-list-item-action>
                    <v-icon @click="openTaskDialog(task)">mdi-pencil</v-icon>
                    <v-icon v-if="!task.completed" @click="completeTask(task)">mdi-checkbox-marked-circle-outline</v-icon>
                    <v-icon v-else @click="completeTask(task)">mdi-checkbox-blank-circle-outline</v-icon>
                    <v-icon @click="deleteTask(task)">mdi-delete</v-icon>
                  </v-list-item-action>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card-text>

          <!-- Diálogo para crear/editar tareas -->
          <v-dialog v-model="taskDialog" persistent max-width="600px">
            <v-card>
              <v-card-title>
                <span>{{ selectedTask.id ? 'Editar Tarea' : 'Nueva Tarea' }}</span>
              </v-card-title>
              <v-card-text>
                <v-text-field v-model="selectedTask.title" label="Título" required outlined></v-text-field>
                <v-text-field v-model="selectedTask.description" label="Descripción" outlined></v-text-field>
                <v-menu ref="menu" v-model="menu" :close-on-content-click="false" :return-value.sync="selectedTask.dueDate" transition="scale-transition" offset-y min-width="auto">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="selectedTask.dueDate" label="Fecha de vencimiento" prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on" outlined></v-text-field>
                  </template>
                  <v-date-picker v-model="selectedTask.dueDate" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="menu = false">Cancelar</v-btn>
                    <v-btn text color="primary" @click="$refs.menu.save(selectedTask.dueDate)">Aceptar</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="taskDialog = false">Cancelar</v-btn>
                <v-btn color="blue darken-1" text @click="saveTask">Guardar</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <!-- Botón para abrir el diálogo de nueva tarea -->
          <v-btn fab dark color="primary" @click="openTaskDialog()" class="mb-2">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.fill-height {
  min-height: 100vh;
}
.elevation-12 {
  box-shadow: 0 12px 16px -8px rgba(0,0,0,0.2);
}
</style>

