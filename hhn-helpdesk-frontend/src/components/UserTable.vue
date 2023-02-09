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
          Vorname
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
        v-for="item in desserts"
        :key="item.name"
      >
        <td>{{ item.name }}</td>
        <td>{{ item.calories }}</td>
        <td>{{ item.calories }}</td>
        <td>{{ item.calories }}</td>
        <td>{{ item.calories }}</td>
        <td><v-btn prepend-icon="mdi-eye" density="compact">Anzeigen</v-btn></td>
      </tr>
    </tbody>
  </v-table>
  <!--<div class="text-center">
    <v-pagination
      
      :length="6"
    ></v-pagination>
  </div>-->
</template>
<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
    setup() {

    },
    data() {
        return {
            desserts: [
            {
            name: 'Frozen Yogurt',
            calories: 159,
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
          },
          {
            name: 'Eclair',
            calories: 262,
          },
          {
            name: 'Cupcake',
            calories: 305,
          },
          {
            name: 'Gingerbread',
            calories: 356,
          },
          {
            name: 'Jelly bean',
            calories: 375,
          },
          {
            name: 'Lollipop',
            calories: 392,
          },
          {
            name: 'Honeycomb',
            calories: 408,
          },
          {
            name: 'Donut',
            calories: 452,
          },
          {
            name: 'KitKat',
            calories: 518,
          },
        ]
        }
    }
})
</script>
