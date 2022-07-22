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

const idValidate = val => {
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
};

const nickValidate = val => {
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
};

const isIdDuple = dupl => {
  if (!dupl) {
    document.querySelector('error-msg.duplid').classList.remove('hidden');
    isCheckId = false;
  } else {
    document.querySelector('valid-msg.id').classList.remove('hidden');
    document.querySelector('error-msg.duplId').classList.add('hidden');
    isCheckId = true;
  }
};

const isnickDup = dupl => {
  if (!dupl) {
    document.querySelector('error-msg.duplNick').classList.remove('hidden');
    isCheckNick = false;
  } else {
    document.querySelector('valid-msg.nick').classList.remove('hidden');
    document.querySelector('error-msgxd.duplNick').classList.add('hidden');
    isCheckNick = true;
  }
};

document.querySelector('body').addEventListener('change', () => {
  // if (isValidId && isValidNick && isValidPwd && isCheckId && isCheckNick) {
  if (isValidId && isValidNick && isValidPwd) {
    const $signUpBtn = document.querySelector('.button.signup-button');
    $signUpBtn.classList.remove('disabled');
    $signUpBtn.disabled = false;
  } else {
    const $signUpBtn = document.querySelector('.button.signup-button');
    $signUpBtn.classList.add('disabled');
    $signUpBtn.disabled = true;
  }
});

export default {
  idValidate,
  pwdValidate,
  isnickDup,
  nickValidate,
  isIdDuple,
};
