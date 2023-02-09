<template>
  <v-app-bar flat >

    <template v-slot:prepend>
          <v-app-bar-nav-icon @click="toggleNavigation"></v-app-bar-nav-icon>
        </template>

    <v-app-bar-title>
      login.hs-heilbronn.de :: Helpdesk
    </v-app-bar-title>
    <v-btn @click="toggleDarkMode"  icon="mdi-theme-light-dark"></v-btn>

    <v-btn append-icon="mdi-logout">Logout</v-btn>
  </v-app-bar>
</template>

<script lang="ts" setup>
  import { useTheme } from 'vuetify'
  import { useAppStore } from '@/store/app';

  const theme = useTheme();
  const appStore = useAppStore();

  function toggleDarkMode() {

    theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark';
  }

  function toggleNavigation() {
    appStore.toggleNavigationDrawer();
  }


</script>
