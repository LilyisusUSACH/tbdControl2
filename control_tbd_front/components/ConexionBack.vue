<script setup>
import { useApp } from '@nuxtjs/app';
import axios from '@/plugins/axios'; // Importar la instancia de Axios

export default defineComponent({
  setup() {
    const app = useApp(); // Obtener la instancia de Nuxt

    // Definición de datos y propiedades
    const taskId = 123; // ID de la tarea
    const [taskDeadline, setTaskDeadline] = useState<Date | null>(null); // Estado para almacenar la fecha y hora de término

    // Función para obtener la fecha y hora de término desde el backend
    const fetchTaskDeadline = async () => {
      try {
        const response = await axios.get(`/api/tasks/${taskId}/deadline`);
        const deadlineData = response.data; // Obtener la fecha y hora de término del backend

        if (deadlineData) {
          const parsedDeadline = new Date(deadlineData); // Convertir la fecha y hora a un objeto Date
          setTaskDeadline(parsedDeadline);
        } else {
          console.warn('No se encontró la fecha y hora de término para la tarea');
        }
      } catch (error) {
        console.error('Error al obtener la fecha y hora de término:', error);
      }
    };

    // Ejecutar la función fetchTaskDeadline al montar el componente
    onMounted(() => {
      fetchTaskDeadline();
    });

    // Exportación de métodos y propiedades
    return {
      taskDeadline,
      fetchTaskDeadline,
    };
  },
});
</script>

<template>
  <div>
    <h1>Componente con Axios y Fecha y Hora de Término</h1>

    <p v-if="taskDeadline">Fecha y hora de término: {{ taskDeadline.toLocaleString() }}</p>
  </div>
</template>

<style scoped>
</style>
