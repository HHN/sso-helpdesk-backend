<template>
    <search-bar />
    <user-table v-if="appStore.searchQuery !== ''"/>
    <user-table-placeholder v-if="appStore.searchQuery === ''"/>
    <reset-dialog v-if="appStore.showResetDialog"/>
  </template>
  
  <script lang="ts" setup>
    //import HelloWorld from '@/components/HelloWorld.vue'
    import SearchBar from '@/components/SearchBar.vue';
    import UserTable from '@/components/UserTable.vue';
    import UserTablePlaceholder from '@/components/UserTablePlaceholder.vue';
    import ResetDialog from '@/components/ResetDialog.vue';
import { useAppStore } from '@/store/app';

    const appStore = useAppStore();
  </script>
  