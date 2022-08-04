import Main from './main';
import SignIn from './signIn';
import Mypage from './mypage';
import edit from './edit';
import SignUp from './signUp';
import Detail from './detail';
import Write from './write';
import NotFound from './notFound';
import Oauth from './ouath';
import OauthSignUp from './oauthSignUp';
import Update from './update';
import Edit from './edit';

const routes = [
  { path: '/', component: Main },
  { path: '/detail', component: Detail },
  { path: '/write', component: Write },
  { path: '/mypage', component: Mypage },
  { path: '/edit', component: Edit },
  { path: '/signin', component: SignIn },
  { path: '/signup', component: SignUp },
  { path: '/oauth2', component: Oauth },
  { path: '/oauthSignUp', component: OauthSignUp },
  { path: '/update', component: Update },
];

const router = () => {
  const path = window.location.pathname.split('/');
  const targetPath = '/' + path[1];
  const route = routes.find(route => route.path === targetPath);
  return route ? route.component() : NotFound();
};

export default router;
