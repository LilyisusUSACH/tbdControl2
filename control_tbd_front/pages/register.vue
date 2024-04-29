import { type } from '../.nuxt/types/imports';
import { ref, useRouter } from 'vue-router';
<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const isLoading = ref(false);
const errorMessage = ref('');
const form = ref(null);
const router = useRouter();

// Reglas de validación
const rules = {
  required: value => !!value || 'Este campo es obligatorio.',
  min: v => (v && v.length >= 8) || 'La contraseña debe tener al menos 8 caracteres.',
};

const onClick = async () => {
  if (!form.value.validate()) {
    return;
  }

  isLoading.value = true;
  errorMessage.value = '';

  try {
    const response = await fetch('http://localhost:8080/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: username.value,
        password: password.value,
        roleRequest: {
          roleListName: ['ADMIN']
        }
      })
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Ocurrió un error al registrarse.');
    }

    //TODO: si no funciona el register ser especifico con el error
    //TODO: es decir que si el usuario ya existe se le notifique al usuario
    isLoading.value = false;

    // Si el registro es exitoso se redirige al usuario al login para que inicie sesión TODO
    router.push('/login');

  } catch (error) {
    isLoading.value = false;
    errorMessage.value = error.message;
  }
}
</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="elevation-12" color="surface-variant">
          <v-toolbar color="primary" dark flat>
            <v-toolbar-title>Registro de usuario</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form ref="form" @submit.prevent="onClick">
              <v-text-field
                  label="Nombre de usuario"
                  v-model="username"
                  :rules="[rules.required]"
                  outlined
                  clearable
              ></v-text-field>
              <v-text-field
                  label="Contraseña"
                  v-model="password"
                  :type="'password'"
                  :rules="[rules.required, rules.min]"
                  outlined
                  clearable
              ></v-text-field>
              <v-alert v-if="errorMessage" type="error" class="mt-3">
                {{ errorMessage }}
              </v-alert>
              <v-progress-circular v-if="isLoading" indeterminate></v-progress-circular>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="isLoading" color="white" @click="onClick">
              Registrarse
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
  box-shadow: 0 6px 10px rgba(0,0,0,0.9);
}
</style>

