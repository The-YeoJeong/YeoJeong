import Main from './main';
import SignIn from './signIn';
import Mypage from './mypage';
// import MypageEdit from './mypageEdit';
import SignUp from './signUp';
// import Develog from './develog';
// import Search from './search';
// import Detail from './detail';
import Write from './write';
import NotFound from './notFound';
import Oauth from './ouath';
import OauthSignUp from './oauthSignUp';

const routes = [
  { path: '/', component: Main },
  // { path: '/callback', component: Main },
  // { path: '/search', component: Search },
  // { path: '/develog', component: Develog },
  // { path: '/detail', component: Detail },
  { path: '/write', component: Write },
  { path: '/mypage', component: Mypage },
  // { path: '/mypageEdit', component: MypageEdit },
  { path: '/signin', component: SignIn },
  { path: '/signup', component: SignUp },
  { path: '/oauth2', component: Oauth},
  { path: '/oauthSignUp', component: OauthSignUp }
];

const router = () => {
  const path = window.location.pathname.split('/');
  const targetPath = '/' + path[1];
  const route = routes.find(route => route.path === targetPath);
  return route ? route.component() : NotFound();
};

export default router;
