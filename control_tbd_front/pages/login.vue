import { type } from '../.nuxt/types/imports';
import { ref, useRouter } from 'vue-router';
<script setup>

const username = ref("");
const password = ref("");
const router = useRouter();
const error = ref('');

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

    if (response && response.jwt) {
      localStorage.setItem('authToken', response.jwt); // Guarda el token
      router.push('/dashboard'); // Redirecciona al usuario al dashboard lo que tiene sentido ya que se ha logueado
    } else {
      throw new Error('No se recibió el token de acceso');
    }
  } catch (err) {
    console.error('Error:', err);
    error.value = err.message || 'Un error ha ocurrido durante el login.';
  }
};


</script>


<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="elevation-12" color="surface-variant">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>Inicio de sesión</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                  v-model="username"
                  label="Nombre de usuario"
                  :rules="[v => !!v || 'El nombre de usuario es requerido']"
                  outlined
                  clearable
              ></v-text-field>
              <v-text-field
                  v-model="password"
                  label="Contraseña"
                  :type="'password'"
                  :rules="[v => !!v || 'La contraseña es requerida']"
                  outlined
                  clearable
              ></v-text-field>
              <v-alert v-if="error" type="error" class="mt-3">
                {{ error }}
              </v-alert>
              <v-progress-circular v-if="isLoading" indeterminate size="40"></v-progress-circular>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="isLoading" color="blue" dark @click="onClick">
              Iniciar sesión
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.fill-height {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.elevation-12 {
  box-shadow: 0 6px 10px rgba(0,0,0,0.1);
  padding: 20px;
}
</style>