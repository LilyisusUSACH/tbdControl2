import { type } from '../.nuxt/types/imports';
import { ref, useRouter } from 'vue-router';
<script setup>

const username = ref("");
const password = ref("");
const router = useRouter();

router.push('/login');

const onClick = async () => {
    try {
        await $fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Origin': 'http://localhost:3000',
                'Host':'http://localhost:3000'
            },
            body: JSON.stringify({
                'username': username.value,
                'password': password.value
            })
        })
    } catch (error) {
        console.error('Error:', error)
    }
}

</script>

<template class="init">
    <v-card elevation="16" color="surface-variant" class="principalCard">
        <v-container style="height: 100%;">
            <v-row style="height: 100%; align-content: space-around;">
                <v-col>
                    <v-form @submit.prevent>
                        <v-text-field label="Username" v-model="username" variant="outlined"></v-text-field>
                        <v-text-field label="Password" v-model="password" variant="outlined"></v-text-field>
                        <v-btn type="submit" @Click="onClick" block>aaa</v-btn>
                    </v-form>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
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
