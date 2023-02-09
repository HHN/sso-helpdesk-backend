<template>
    <v-table density="compact">
    <thead>
      <tr>
        <th class="text-left">
          Benutzername
        </th>
        <th class="text-left">
          Nachname
        </th>
        <th class="text-left">
          Vorname
        </th>
        <th class="text-left">
          E-Mail
        </th>
        <th class="text-left">
          ID (M/P)
        </th>
        <th class="text-left">
          Aktionen
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="item in appStore.resultList"
        :key="item.keycloakId"
      >
        <td>{{ item.username }}</td>
        <td>{{ item.lastName }}</td>
        <td>{{ item.firstName }}</td>
        <td>{{ item.email }}</td>
        <td>{{ item.id }}</td>
        <td><v-btn prepend-icon="mdi-lock-reset" density="compact" @click="openResetDialog(item)">Login zurücksetzen</v-btn></td>
      </tr>
    </tbody>
  </v-table>
  <!--<div class="text-center">
    <v-pagination

      :length="6"
    ></v-pagination>
  </div>-->
</template>
<script lang="ts" setup>
import { User } from '@/models/User';
import { resetCredential } from '@/services/UserService';
import { useAppStore } from '@/store/app';

const appStore = useAppStore();

function openResetDialog(user: User) {
    const seq = prompt("Laufnummer?");
    console.log(seq);
    if (seq != null) {
        resetCredential(user.keycloakId, seq);
    }

}

</script>
