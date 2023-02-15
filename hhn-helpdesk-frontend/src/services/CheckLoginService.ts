import { showAlertMessage } from "./AlertMessageService";


export function checkIfUserIsLoggedIn(promise: Promise<any>) {
    return promise.catch((e: { response: { status: number; }; }) => {
        if (e.response.status === 403) {
            showAlertMessage("Sie sind nicht mehr angemeldet. Sie müssen sich erneut anmelden und die vorige Aktion wiederholen. Sie werden in 2 Sekunden weitergeleitet...", "error");
            setTimeout(function()
            {
                redirectToLogin();

            }, 2000);
            
        } else {
            throw e;
        }
    });
    
}

export function redirectToLogin() {
    window.location.href = "/sso/login";
}