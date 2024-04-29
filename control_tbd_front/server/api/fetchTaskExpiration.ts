import axios from '@/plugins/axios';

export async function fetchTaskExpiration(taskId: number) {
    try {
        const response = await axios.get(`/api/tarea/${taskId}/expiration`);
        const taskExpirationData = response.data;

        if (taskExpirationData) {
            // Procesar la información de expiración (ej: mostrar fecha, calcular tiempo restante)
            console.log('Información de expiración:', taskExpirationData);
            return taskExpirationData; // Devolver la información de expiración
        } else {
            console.warn('No se encontró la información de expiración para la tarea');
            return null; // Indicar que no se encontró la información
        }
    } catch (error) {
        console.error('Error al obtener la información de expiración:', error);
        return null; // Indicar error
    }
}