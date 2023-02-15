<template>
  <v-app-bar flat >

    <template v-slot:prepend>
          <v-app-bar-nav-icon @click="toggleNavigation"></v-app-bar-nav-icon>
        </template>

    <v-app-bar-title>
      login.hs-heilbronn.de :: Helpdesk {{ appStore.currentUser.username === "N/A" ? "" : " :: " +  appStore.currentUser.username}}
    </v-app-bar-title>
    <v-btn @click="toggleDarkMode"  icon="mdi-theme-light-dark"></v-btn>

    <v-btn append-icon="mdi-logout" @click="performLogout()">Logout</v-btn>
  </v-app-bar>
</template>

<script lang="ts" setup>
  import { useTheme } from 'vuetify'
  import { useAppStore } from '@/store/app';
import { logout } from '@/services/LogoutService';

  const theme = useTheme();
  const appStore = useAppStore();

  function toggleDarkMode() {

    theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark';
  }

  function toggleNavigation() {
    appStore.toggleNavigationDrawer();
  }

  function performLogout() {
    logout();
  }


</script>
