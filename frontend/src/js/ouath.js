import axios from 'axios';

const path = new URL(window.location.href);
const oauth = path.searchParams;

console.log(oauth.get('code'))
console.log(oauth.get('state'))

var check = window.location.pathname.split("/")[2]

async function LoginWithCode() {
    const { data } = await axios.get('/api/oauth2/' + check + '/login?code=' + oauth.get('code') + '&state=' + oauth.get('state'));
    console.log("jwt : " + data.jwt)
    console.log("accessToken : " + data.accessToken)

    if (data.accessToken != null) {
        window.localStorage.setItem('oauthCheck', check)
        window.localStorage.setItem('accessToken', data.accessToken)
        window.history.pushState(null, null, '/oauthSignUp');
    } else if (data.jwt != null) {
        window.localStorage.setItem('jwt', data.jwt)
        window.history.pushState(null, null, '/');
    }
}

export default LoginWithCode;
