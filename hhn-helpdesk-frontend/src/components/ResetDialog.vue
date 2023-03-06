<template>
     <v-dialog
      v-model="appStore.showResetDialog"
      max-width="600px"
    >
      <v-card>
        <h3 style="text-align: center;">Zurücksetzen von "{{ appStore.currentResetUser.content?.firstName + " " +  appStore.currentResetUser.content?.lastName  + " (" + appStore.currentResetUser.content?.id + ")" }}"</h3>
        <div id="reader"></div>
        <v-row>
            
        </v-row>
        <v-row>
            <v-text-field
        variant="solo"
        v-model="seq"
        label="Laufnummer"
        append-inner-icon="mdi-arrow-right-bold-outline"
        single-line
        hide-details
        @click:append-inner="handleSeqSubmit"
        v-on:keyup.enter="handleSeqSubmit"
      ></v-text-field>
        </v-row>
        

        <v-card-text>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" block @click="appStore.currentResetUser = nullUser; appStore.showResetDialog = false">Abbrechen</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <h1>Test</h1>
</template>
<script lang="ts" setup>
import { User } from "@/models/User";
import { nullUser } from "@/models/UserOrNull";
import { resetCredential } from "@/services/UserService";
import { useAppStore } from "@/store/app";
import {Html5QrcodeScanner} from "html5-qrcode"

import { onBeforeUnmount, onMounted, ref } from "vue";

const appStore = useAppStore();

const seq = ref<string>();


function handleSeqSubmit() {
    startResetCredential(seq.value!)
}



function startResetCredential(seq: string) {
    if (seq != null && seq!= undefined) {
        resetCredential(appStore.currentResetUser.content?.keycloakId!, seq);
    }
} 


function onScanSuccess(decodedText: any, decodedResult: any) {
  console.log("decodedText: " + decodedText);
  startResetCredential(decodedText);
}

function onScanFailure(error: any) {
    console.log("onScanFailure: " + error);

}

let html5QrcodeScanner: any = null;

function init() {
    html5QrcodeScanner = new Html5QrcodeScanner(
  "reader",
  { fps: 10, qrbox: {width: 250, height: 250} },
  /* verbose= */ false);
html5QrcodeScanner.render(onScanSuccess, onScanFailure);
}

onMounted(() => {
    init();
});


onBeforeUnmount(() => {
    if (html5QrcodeScanner != null) {
        html5QrcodeScanner.clear();
        console.log("cleared qr code reader");
    }
});





</script>