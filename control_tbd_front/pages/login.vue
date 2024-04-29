import { type } from '../.nuxt/types/imports';
import { ref, useRouter } from 'vue-router';
<script setup>

const username = ref("");
const password = ref("");
const router = useRouter();
const error = ref('');  // Declaración de la variable de error

const onClick = async () => {
  try {
    const response = await $fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Origin': 'http://localhost:3000',
      },
      body: JSON.stringify({
        'username': username.value,
        'password': password.value
      })
    });

    // Asegúrate de acceder al token usando la clave 'jwt', que es como viene en tu respuesta de Postman
    if (response && response.jwt) {
      localStorage.setItem('authToken', response.jwt); // Guarda el token
      router.push('/dashboard'); // Redirecciona al usuario al dashboard
    } else {
      throw new Error('No se recibió el token de acceso');
    }
  } catch (err) {
    console.error('Error:', err);
    error.value = err.message || 'Un error ha ocurrido durante el login.';
  }
};



</script>

<template class="init">
    <v-card elevation="16" color="surface-variant" class="principalCard">
        <v-container style="height: 100%;">
            <v-row style="height: 100%; align-content: space-around;">
                <v-col>
                    <v-form @submit.prevent>
                        <v-text-field label="Username" v-model="username" variant="outlined"></v-text-field>
                        <v-text-field label="Password" v-model="password" variant="outlined"></v-text-field>
                        <v-btn type="submit" @click="onClick" block>aaa</v-btn>
                    </v-form>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
  <v-alert v-if="error" type="error" class="mb-4">
    {{ error }}
  </v-alert>

</template>

<style>
.init {
    position: relative;
}

.principalCard {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 50vw;
    height: 60vh;
    transform: translate(-50%, -50%);
}
</style>
