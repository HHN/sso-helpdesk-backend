import { useAppStore } from "@/store/app";

const appStore = useAppStore();

export function showAlertMessage(msg: string, type: string) {
    appStore.alertMessage = msg;
    appStore.alertMessageType = type;
    appStore.showAlertMessage = true;
}