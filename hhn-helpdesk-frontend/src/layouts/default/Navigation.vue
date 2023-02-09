<template>
    <v-navigation-drawer
    v-if="appStore.navigationDrawerOpen"
    permanent

      >
        <v-list
          nav
        >
          <v-list-item prepend-icon="mdi-account" title="Benutzer" value="users" :to="'users'"></v-list-item>
          <v-list-item prepend-icon="mdi-email-newsletter" title="Zugangsdaten erzeugen" value="credentials" :to="'credentials'"></v-list-item>
          <v-list-item prepend-icon="mdi-text-box-check" title="Auditlog" value="auditlog" :to="'audit'"></v-list-item>
          <v-list-item prepend-icon="mdi-help" title="Hilfe" value="help" :to="'help'"></v-list-item>
          <v-list-item prepend-icon="mdi-format-pilcrow-arrow-right" title="Impressum" value="imprint" :to="'imprint'"></v-list-item>

        </v-list>
</v-navigation-drawer>
</template>
<script lang="ts" setup>
import { useAppStore } from '@/store/app';
const appStore = useAppStore();
</script>

