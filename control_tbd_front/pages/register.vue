<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const isLoading = ref(false);
const errorMessage = ref('');
const form = ref(null);

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

    // Si el registro es exitoso, puedes redirigir o manejar el estado de autenticación aquí.
    isLoading.value = false;
    // Por ejemplo: this.$router.push('/dashboard');

  } catch (error) {
    isLoading.value = false;
    errorMessage.value = error.message;
  }
}
</script>

<template>
  <div class="container">
    <v-card elevation="16" color="surface-variant" class="principalCard">
      <v-container>
        <v-row justify="center">
          <v-col cols="12" sm="8" md="6">
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
              <v-btn :disabled="isLoading" type="submit" color="primary" block>
                {{ isLoading ? 'Registrando...' : 'Registrarse' }}
              </v-btn>
              <v-alert v-if="errorMessage" type="error" class="mt-3">
                {{ errorMessage }}
              </v-alert>
              <v-progress-circular v-if="isLoading" indeterminate></v-progress-circular>
            </v-form>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.principalCard {
  max-width: 600px;
  width: 100%;
}
</style>
