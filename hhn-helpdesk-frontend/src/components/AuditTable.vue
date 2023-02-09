<template>
    <v-table density="compact">
    <thead>
      <tr>
        <th class="text-left">
          Id
        </th>
        <th class="text-left">
          Zeitstempel
        </th>
        <th class="text-left">
          Aktor
        </th>
        <th class="text-left">
          Aktion
        </th>
        <th class="text-left">
          Parameter
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="item in appStore.auditList"
        :key="item.id"
      >
        <td>{{ item.id }}</td>
        <td>{{ item.time }}</td>
        <td>{{ item.actor }}</td>
        <td>{{ item.action }}</td>
        <td>{{ item.params }}</td>
      </tr>
    </tbody>
  </v-table>
</template>
<script lang="ts" setup>
import { fetchAuditEntries } from '@/services/AuditService';
import { useAppStore } from '@/store/app';

const appStore = useAppStore();


fetchAuditEntries();

</script>