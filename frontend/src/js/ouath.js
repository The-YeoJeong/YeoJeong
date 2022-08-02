import axios from 'axios';

const path = new URL(window.location.href);
const oauth = path.searchParams;

console.log(oauth.get('code'))
console.log(oauth.get('state'))

var check = window.location.pathname.split("/")[2]

function LoginWithCode() {
    axios.get('/api/oauth2/' + check + '/login?code=' + oauth.get('code') + '&state=' + oauth.get('state')).then(function (response) {
        console.log("jwt : " + response.data.jwt)
        console.log("accessToken : " + response.data.accessToken)

        if (response.data.accessToken != null) {
            window.localStorage.setItem('oauthCheck', check)
            window.localStorage.setItem('accessToken', response.data.accessToken)
            window.history.pushState(null, null, '/oauthSignUp');
        } else if (response.data.jwt != null) {
            window.localStorage.setItem('jwt', response.data.jwt)
        }
    });
}

export default LoginWithCode;
