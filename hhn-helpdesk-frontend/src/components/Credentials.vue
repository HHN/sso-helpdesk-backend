<template>

    <v-form @submit.prevent="submitCreateCredentialForm">
        <v-select v-model="selectedLocation"   label="Standort" :items="appStore.locationList" item-title="label" item-value="id" />
        <v-text-field v-model="amount"  label="Anzahl" placeholder="50" type="number" :rules="amountRules" />
        <v-btn type="submit">Zugangsdaten erzeugen</v-btn>
    </v-form>


    <v-overlay v-model="appStore.isWaiting" class="align-center justify-center" >
        <v-progress-circular indeterminate :size="128" :width="12"  />
    </v-overlay>

    <h2>Nutzungsübersicht</h2>


    <v-table density="compact">
    <thead>
      <tr>
        <th class="text-left">
          Standort
        </th>
        <th class="text-left">
          Nutzbar
        </th>
        <th class="text-left">
          Gesamt
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="item in appStore.locationList"
        :key="item.id"
      >
        <td>{{ item.label }}</td>
        <td>{{ item.free }}</td>
        <td>{{ item.total }}</td>
      </tr>
    </tbody>
  </v-table>

</template>
<script lang="ts" setup>
import { createCredentials } from '@/services/CreateCredentialService';
import { fetchLocations } from '@/services/LocationService';
import { useAppStore } from '@/store/app';
import { ref } from 'vue';
import { Location } from '@/models/Location';

const selectedLocation = ref<number>();
    const amount = ref<string>();


const appStore = useAppStore();

fetchLocations();

const amountRules = [
    (v: number) => !!v || "Die Anzahl wird benötigt",
    (v: number) => (v && v >= 1) || "Die Anzahl muss positiv sein",
    (v: number) => (v && v <= 50) || "Die Anzahl darf maximal 50 betragen. Falls mehr benötigt werden, bitte mehrfach durchführen!",
];

function submitCreateCredentialForm() {
    console.log(selectedLocation.value)
    createCredentials(selectedLocation.value!, parseInt(amount.value!));
}



</script>
