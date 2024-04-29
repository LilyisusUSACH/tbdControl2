<script>
import WebSocketClient from '@/plugins/websocket'; // Importar clase WebSocketClient



export default {
  data() {
    return {
      messages: [], // Lista de mensajes recibidos
      socketClient: null, // Instancia de WebSocketClient
      messageText: '', // Texto del mensaje a enviar
    };
  },
  mounted() {
    // Crear instancia de WebSocketClient y conectar al servidor
    this.socketClient = new WebSocketClient('ws://localhost:3000');

    // Manejar la recepción de mensajes
    this.socketClient.onmessage = (message) => {
      const parsedMessage = JSON.parse(message.data);
      this.messages.push(parsedMessage);
    };
  },
  methods: {
    sendMessage() {
      // Validar que el texto del mensaje no esté vacío
      if (this.messageText.trim()) {
        const message = {
          text: this.messageText, // Enviar el texto del mensaje
        };
        this.socketClient.send(JSON.stringify(message)); // Enviar mensaje al servidor
        this.messageText = ''; // Limpiar el campo de texto
      } else {
        console.warn('El mensaje no puede estar vacío');
      }
    },
  },
};
</script>

<template>
  <div>
    <h1>Componente con WebSockets</h1>

    <div class="message-input">
      <input type="text" v-model="messageText" placeholder="Ingresa tu mensaje">
      <button @click="sendMessage">Enviar</button>
    </div>

    <ul class="messages">
      <li v-for="message in messages" :key="message.id">{{ message.text }}</li>
    </ul>
  </div>
</template>

<style scoped>
.message-input {
  display: flex;
  margin-bottom: 10px;
}

.message-input input {
  flex: 1;
  padding: 5px;
  border: 1px solid #ccc;
}

.message-input button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

.messages {
  list-style: none;
  padding: 0;
}

.messages li {
  padding: 5px;
  border: 1px solid #ccc;
  margin-bottom: 5px;
}
</style>
