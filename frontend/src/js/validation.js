const rule = {
  //영어 10자리 이내
  id: /^[A-Za-z]{1,10}$/,
  //영어,숫자,특수문자 포함 6~10자리
  password: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,10}$/,
  //영어, 한글 10자리 이내
  nickName: /^[0-9A-Za-zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{1,10}$/,
};

let isValidId = false;
let isValidNick = false;
let isValidPwd = false;
let isCheckId = false;
let isCheckNick = false;
let isOAuthCheckId = false;
let isOAuthCheckNick = false;

const activeSignUpBtn = () => {
  if ((isValidId && isValidNick && isValidPwd && isCheckId && isCheckNick) || (isOAuthCheckId && isOAuthCheckNick)) {
    const $signUpBtn = document.querySelector('.button.signup-button');
    $signUpBtn.classList.remove('disabled');
    $signUpBtn.disabled = false;
  } else {
    const $signUpBtn = document.querySelector('.button.signup-button');
    $signUpBtn.classList.add('disabled');
    $signUpBtn.disabled = true;
  }
};

const idValidate = val => {
  document.querySelector('.valid-msg.idDupVal').classList.add('hidden');
  isCheckId = false;
  if (!rule.id.test(val)) {
    document.querySelector('.error-msg.idVal').classList.remove('hidden');
    document.querySelector('.valid-msg.idVal').classList.add('hidden');
    isValidId = false;
    document.querySelector('.button.dupl_id').disabled = true;
  } else {
    document.querySelector('.error-msg.idVal').classList.add('hidden');
    document.querySelector('.valid-msg.idVal').classList.remove('hidden');
    document.querySelector('.button.dupl_id').disabled = false;
    isValidId = true;
  }
  activeSignUpBtn();
};

const pwdValidate = (val, val2) => {
  if (!rule.password.test(val)) {
    document.querySelector('.error-msg.pwdVal').classList.remove('hidden');
    document.querySelector('.valid-msg.pwdVal').classList.add('hidden');
    isValidPwd = false;
  } else {
    document.querySelector('.error-msg.pwdVal').classList.add('hidden');
    document.querySelector('.valid-msg.pwdVal').classList.remove('hidden');
    isValidPwd = true;
  }
  if (val2) {
    if (val !== val2) {
      document.querySelector('.error-msg.pwdChk').classList.remove('hidden');
      document.querySelector('.valid-msg.pwdChk').classList.add('hidden');
      isValidPwd = false;
    } else {
      document.querySelector('.error-msg.pwdChk').classList.add('hidden');
      document.querySelector('.valid-msg.pwdChk').classList.remove('hidden');
      isValidPwd = true;
    }
  }
  activeSignUpBtn();
};

const nickValidate = val => {
  document.querySelector('.valid-msg.nickDupVal').classList.add('hidden');
  isCheckNick = false;
  if (!rule.nickName.test(val)) {
    document.querySelector('.error-msg.nick').classList.remove('hidden');
    document.querySelector('.valid-msg.nick').classList.add('hidden');
    document.querySelector('.button.dupl_nick').disabled = true;
    isValidNick = false;
  } else {
    document.querySelector('.error-msg.nick').classList.add('hidden');
    document.querySelector('.valid-msg.nick').classList.remove('hidden');
    isValidNick = true;
    document.querySelector('.button.dupl_nick').disabled = false;
  }
  activeSignUpBtn();
};

const isIdDuple = dupl => {
  if (!dupl) {
    document.querySelector('.error-msg.duplId').classList.remove('hidden');
    document.querySelector('.valid-msg.idDupVal').classList.add('hidden');
    isCheckId = false;
  } else {
    document.querySelector('.valid-msg.idVal').classList.add('hidden');
    document.querySelector('.valid-msg.idDupVal').classList.remove('hidden');
    document.querySelector('.error-msg.duplId').classList.add('hidden');
    isCheckId = true;
  }
  activeSignUpBtn();
};

const isnickDup = dupl => {
  if (!dupl) {
    document.querySelector('.error-msg.duplNick').classList.remove('hidden');
    document.querySelector('.valid-msg.nickDupVal').classList.add('hidden');
    isCheckNick = false;
  } else {
    document.querySelector('.valid-msg.nickDupVal').classList.remove('hidden');
    document.querySelector('.error-msg.duplNick').classList.add('hidden');
    document.querySelector('.valid-msg.nick').classList.add('hidden');
    isCheckNick = true;
  }
  activeSignUpBtn();
};

const isOAuthIdDuple = dupl => {
  if (!dupl) {
    document.querySelector('.error-msg.duplId').classList.remove('hidden');
    document.querySelector('.valid-msg.idDupVal').classList.add('hidden');
    isOAuthCheckId = false;
  } else {
    document.querySelector('.valid-msg.idVal').classList.add('hidden');
    document.querySelector('.valid-msg.idDupVal').classList.remove('hidden');
    document.querySelector('.error-msg.duplId').classList.add('hidden');
    isOAuthCheckId = true;
  }
  activeSignUpBtn();
};

const isOAuthNickDup = dupl => {
  if (!dupl) {
    document.querySelector('.error-msg.duplNick').classList.remove('hidden');
    document.querySelector('.valid-msg.nickDupVal').classList.add('hidden');
    isOAuthCheckNick = false;
  } else {
    document.querySelector('.valid-msg.nickDupVal').classList.remove('hidden');
    document.querySelector('.error-msg.duplNick').classList.add('hidden');
    document.querySelector('.valid-msg.nick').classList.add('hidden');
    isOAuthCheckNick = true;
  }
  activeSignUpBtn();
};

export default {
  idValidate,
  pwdValidate,
  isnickDup,
  nickValidate,
  isIdDuple,
  isOAuthIdDuple,
  isOAuthNickDup
};
