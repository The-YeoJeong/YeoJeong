const rule = {
  //영어 10자리 이내
  id: /^[A-Za-z]{1,10}$/,
  //영어,숫자,특수문자 포함 6~10자리
  password: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,10}$/,
  //영어, 한글 10자리 이내
  nickName: /^[0-9A-Za-zㄱ-ㅎ|ㅏ-ㅣ|가-힣]{1,10}$/,
};

const idValidate = val => {
  if (!rule.id.test(val)) {
    document.querySelector('.error-msg.idVal').classList.remove('hidden');
    document.querySelector('.valid-msg.idVal').classList.add('hidden');
  } else {
    document.querySelector('.error-msg.idVal').classList.add('hidden');
    document.querySelector('.valid-msg.idVal').classList.remove('hidden');
  }
};

const pwdValidate = (val, val2) => {
  if (!rule.password.test(val)) {
    document.querySelector('.error-msg.pwdVal').classList.remove('hidden');
    document.querySelector('.valid-msg.pwdVal').classList.add('hidden');
  } else {
    document.querySelector('.error-msg.pwdVal').classList.add('hidden');
    document.querySelector('.valid-msg.pwdVal').classList.remove('hidden');
  }
  if (val2) {
    if (val !== val2) {
      document.querySelector('.error-msg.pwdChk').classList.remove('hidden');
      document.querySelector('.valid-msg.pwdChk').classList.add('hidden');
    } else {
      document.querySelector('.error-msg.pwdChk').classList.add('hidden');
      document.querySelector('.valid-msg.pwdChk').classList.remove('hidden');
    }
  }
};

const nickValidate = val => {
  if (!rule.nickName.test(val)) {
    document.querySelector('.error-msg.nick').classList.remove('hidden');
    document.querySelector('.valid-msg.nick').classList.add('hidden');
  } else {
    document.querySelector('.error-msg.nick').classList.add('hidden');
    document.querySelector('.valid-msg.nick').classList.remove('hidden');
  }
};

const isIdDuple = dupl => {
  if (!dupl) document.querySelector('error-msg.duplid').classList.remove('hidden');
  else {
    document.querySelector('valid-msg.id').classList.remove('hidden');
    document.querySelector('error-msg.duplId').classList.add('hidden');
  }
};

const isnickDup = dupl => {
  if (!dupl) document.querySelector('error-msg.duplNick').classList.remove('hidden');
  else {
    document.querySelector('valid-msg.nick').classList.remove('hidden');
    document.querySelector('error-msgxd.duplNick').classList.add('hidden');
  }
};

export default {
  idValidate,
  pwdValidate,
  isnickDup,
  nickValidate,
  isIdDuple,
};
