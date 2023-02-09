<template>
    <v-text-field
        density="compact"
        variant="solo"
        v-model="query"
        label="Nutzer suchen (Name, Benutzername, Matrikelnummer)"
        append-inner-icon="mdi-magnify"
        single-line
        hide-details
        @click:append-inner="handleSearchSubmit"
        @update:model-value="handleUpdatedText"
        v-on:keyup.enter="handleSearchSubmit"
      ></v-text-field>
</template>
<script lang="ts" setup>
    import { fetchUsers } from '@/services/UserService';
    import { useAppStore } from '@/store/app';
    import { ref } from 'vue';

    const query = ref<string>();
    
    function handleSearchSubmit() {
        useAppStore().searchQuery = query.value!; // todo "!"
        console.log(query.value);

        // perform search
        fetchUsers("test");
    }

    function handleUpdatedText() {
        console.log("ignore handleUpdatedText. Press the search button to search");
    }

    


</script>