/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/js/app.js":
/*!***********************!*\
  !*** ./src/js/app.js ***!
  \***********************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _main__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./main */ "./src/js/main.js");
/* harmony import */ var _notFound__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./notFound */ "./src/js/notFound.js");


// import Signin from './signin';
// import Mypage from './mypage';
// import MypageEdit from './mypageEdit';
// import Signup from './signup';
// import Develog from './develog';
// import Search from './search';
// import Detail from './detail';
// import Write from './write';


const routes = [
  { path: '/', component: _main__WEBPACK_IMPORTED_MODULE_0__["default"] },
//   { path: '/callback', component: Main },
//   { path: '/search', component: Search },
//   { path: '/develog', component: Develog },
//   { path: '/detail', component: Detail },
//   { path: '/write', component: Write },
//   { path: '/mypage', component: Mypage },
//   { path: '/mypageEdit', component: MypageEdit },
//   { path: '/signin', component: Signin },
//   { path: '/signup', component: Signup },
];

const router = () => {
  const path = window.location.pathname.split('/');
  const targetPath = '/' + path[1];
  const route = routes.find(route => route.path === targetPath);
  return route ? route.component() : (0,_notFound__WEBPACK_IMPORTED_MODULE_1__["default"])();
};

/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (router);

/***/ }),

/***/ "./src/js/main.js":
/*!************************!*\
  !*** ./src/js/main.js ***!
  \************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _html_main_html__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../html/main.html */ "./src/html/main.html");


const mainNode = () => {
  const node = document.createElement('div');
  node.innerHTML = _html_main_html__WEBPACK_IMPORTED_MODULE_0__["default"];
  if (window.location.pathname !== '/') window.history.pushState(null, null, '/');

  // Event
  node.querySelector('.testBtn').addEventListener('click', () => console.log('click'));

  return node.children;
};

/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (mainNode);


/***/ }),

/***/ "./src/js/notFound.js":
/*!****************************!*\
  !*** ./src/js/notFound.js ***!
  \****************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var _html_notFound_html__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../html/notFound.html */ "./src/html/notFound.html");


const notFoundNode = () => {
  const node = document.getElementById('root');
  node.innerHTML = _html_notFound_html__WEBPACK_IMPORTED_MODULE_0__["default"];

  return node.children;
};

/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (notFoundNode);

/***/ }),

/***/ "./src/html/main.html":
/*!****************************!*\
  !*** ./src/html/main.html ***!
  \****************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
// Module
var code = "<section class=\"top3-container\">\r\n  <span>인기많은 게시글</span>\r\n  <div>좋아효</div>\r\n  <button class=\"testBtn\">test</button>\r\n</section>\r\n";
// Exports
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (code);

/***/ }),

/***/ "./src/html/notFound.html":
/*!********************************!*\
  !*** ./src/html/notFound.html ***!
  \********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
// Module
var code = "<main>\r\n  <span id=\"not-found\">존재하지 않는 페이지 입니다. </span>\r\n</main>\r\n";
// Exports
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (code);

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
var __webpack_exports__ = {};
// This entry need to be wrapped in an IIFE because it need to be isolated against other entry modules.
(() => {
var __webpack_exports__ = {};
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _js_app__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./js/app */ "./src/js/app.js");

// import createHeaderNode from './js/header';

// const history = require('history-events');

const $root = document.getElementById('root');

// const render = async elem => {
//   const headerNode = await createHeaderNode();
//   $root.replaceChildren(...headerNode, ...elem);
// };

const render = async elem => {
  $root.replaceChildren(...elem);
};

render((0,_js_app__WEBPACK_IMPORTED_MODULE_0__["default"])());

// url 변경감지
window.addEventListener('changestate', () => {
  render((0,_js_app__WEBPACK_IMPORTED_MODULE_0__["default"])());
});

})();

// This entry need to be wrapped in an IIFE because it need to be isolated against other entry modules.
(() => {
/*!*****************************!*\
  !*** ./src/scss/index.scss ***!
  \*****************************/
__webpack_require__.r(__webpack_exports__);
// extracted by mini-css-extract-plugin

})();

/******/ })()
;
//# sourceMappingURL=index.js.map