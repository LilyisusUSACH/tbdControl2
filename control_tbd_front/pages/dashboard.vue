import { type } from '../.nuxt/types/imports';
<template>
  <v-container>
    <!-- Filtros y Búsqueda -->
    <v-row>
      <v-col>
        <v-text-field label="Buscar" v-model="search" outlined clearable></v-text-field>
      </v-col>
      <v-col>
        <v-select label="Estado" v-model="filter" :items="['Todas', 'Pendientes', 'Completadas']" outlined></v-select>
      </v-col>
    </v-row>

    <!-- Lista de Tareas -->
    <v-list>
      <v-list-item v-for="task in filteredTasks" :key="task.id">
        <v-list-item-content>
          <v-list-item-title>{{ task.title }}</v-list-item-title>
          <v-list-item-subtitle>{{ task.description }}</v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <v-btn color="primary" @click="editTask(task)">Editar</v-btn>
          <v-btn color="success" @click="completeTask(task)">Completar</v-btn>
          <v-btn color="error" @click="deleteTask(task)">Eliminar</v-btn>
        </v-list-item-action>
      </v-list-item>
    </v-list>

    <!-- Botón para crear tarea -->
    <v-btn color="primary" @click="createTask">Crear Tarea</v-btn>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      tasks: [], // Aquí irían las tareas
      search: '',
      filter: 'Todas',
    };
  },
  computed: {
    filteredTasks() {
      return this.tasks.filter(task => {
        const searchText = this.search.toLowerCase();
        return (
            (task.title.toLowerCase().includes(searchText) || task.description.toLowerCase().includes(searchText)) &&
            (this.filter === 'Todas' ||
                (this.filter === 'Pendientes' && !task.completado) ||
                (this.filter === 'Completadas' && task.completado))
        );
      });
    }
  },

  methods: {
    createTask() {
      // Aquí iría la lógica para crear una tarea
    },
    editTask(task) {
      // Aquí iría la lógica para editar una tarea
    },
    completeTask(task) {
      const index = this.tasks.indexOf(task);
      if (index !== -1) {
        this.tasks[index].completado = true;
        // Aquí enviarías una solicitud PUT al servidor para actualizar la tarea
      }
    },
    deleteTask(task) {
      const index = this.tasks.indexOf(task);
      if (index !== -1) {
        this.tasks.splice(index, 1);
        // Aquí enviarías una solicitud DELETE al servidor para eliminar la tarea
      }
    },
  },
};
</script>
