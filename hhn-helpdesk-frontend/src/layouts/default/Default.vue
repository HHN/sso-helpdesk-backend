<template>
  <v-app>
    <default-bar />
    <default-navigation />

    <default-view />

    <v-overlay v-model="appStore.isWaiting" class="align-center justify-center" >
        <v-progress-circular indeterminate :size="128" :width="12"  />
    </v-overlay>

    <v-snackbar
      v-model="appStore.showAlertMessage"
      :timeout="2000"
      :color="appStore.alertMessageType"
    >
      {{ appStore.alertMessage }}

      <template v-slot:action="{ attrs }">
        <v-btn
          color="blue"
          text
          v-bind="attrs"
          @click="appStore.showAlertMessage = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>

    
  </v-app>
</template>

<script lang="ts" setup>
  import DefaultBar from './AppBar.vue'
  import DefaultView from './View.vue'
  import DefaultNavigation from './Navigation.vue'

  import { useAppStore } from '@/store/app';

  const appStore = useAppStore()
</script>
