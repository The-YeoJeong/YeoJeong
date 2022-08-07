import axios from 'axios';

//main page
const renderTop3posts = async container => {
  const { data } = await axios.get('/api/main/post/top');
  const top3post = data
    .map(
      post =>
        `  <div class="top-post" data-id=${post.postNo}>
    <div class="wrapper">
      <span class="top-post__title">${post.postTitle}</span>
      <span class="top-post__heart-wrapper"> <i class="fas fa-heart"></i><span class="top-post__likenum">${post.postHeartCnt}</span> </span>
    </div>
    <span class="top-post__user">${post.memberNickname}님</span>
    <img class="top_post__img"></img>
  </div>`
    )
    .join('');
  container.innerHTML = top3post;
};

const renderPosts = (data, container) => {
  const posts = data
    .map(
      post => `<div class="mainpost" data-id="${post.postNo}">
      <div class="mainpost__left">
        <span class="mainpost__user">${post.memberNickname}(${post.memberId})</span>
        <span class="mainpost__title">${post.postTitle}</span>
        <div class="mainpost-wrapper">
          <i class="fas fa-heart main" style="color: orangered"></i><span class="mainpost__heartnum">${
            post.postHeartCnt
          }</span
          ><span class="mainpost__date">${post.createdTime.substring(0, 10)}</span>
        </div>
      </div>
      <img class="mainpost__img" scr="" /></div>`
    )
    .join('');
  container.innerHTML = posts;
};

//write page
const addDataCard = container => {
  container.insertAdjacentHTML(
    'beforeend',
    `<details class="date-card">
  <summary><input class="date-card__title" placeholder="1일차" /><i class="fa-solid fa-xmark"></i></summary>
  <div class="schedule-card-container">
    <fieldset class="schedule-card">
      <div class="schedule-card__location">
        <i class="fa-solid fa-xmark"></i>
        <label class="location__name" for="location__name">장소명</label>
        <input id="location__name" type="text" />
        <label class="location__addr" for="location__addr">주소</label>
        <input id="location__addr" type="text" disabled />
      </div>
      <label class="memo" for="memo">메모</label>
      <textarea
        id="memo"
        name="story"
        rows="5"
        cols="33"
        placeholder="[작성예시]
총 비용 : 25000원 
이동수단 : 버스 
주변에 공원 있으니까 사진 찍기
          "
      ></textarea>
    </fieldset>
  </div>
  <div class="date-card__buttons">
    <button class="add-schedule-button">+</button>
  </div>
</details>`
  );
};

let positions = [];

const makePositions = dateCards => {
  dateCards.forEach(dateCard => {
    dateCard.postScheduleCard.forEach(schedule => {
      positions.push({ no: schedule.postSchedulecardNo, addr: schedule.placeAddress, addr_name: schedule.placeName });
    });
  });
  console.log(positions);
};

const addScheduleCard = container => {
  container.insertAdjacentHTML(
    'beforeend',
    `<fieldset class="schedule-card">
        <div class="schedule-card__location">
          <i class="fa-solid fa-xmark"></i>
          <label class="location__name" for="location__name">장소명</label>
          <input id="location__name" type="text" />
          <label class="location__addr" for="location__addr">주소</label>
          <input id="location__addr" type="text" disabled placeholder="주소 검색"/>
        </div>
        <label class="memo" for="memo">메모</label>
        <textarea
          id="memo"
          name="story"
          rows="5"
          cols="33"
          placeholder="[작성예시]
총 비용 : 25000원
이동수단 : 버스 주변에 공원 있으니까 사진 찍기
              "
        ></textarea>
      </fieldset>`
  );
};

const makeScheduleCardNode = scheduleCards => {
  return scheduleCards
    .map(
      scheduleCard =>
        `<fieldset class="schedule-card">
     <div class="schedule-card__location">
       <label class="location__name" for="location__name" disabled>장소명:</label>
       <input id="location__name" value="${scheduleCard.placeName}" type="text" disabled/>
       <label class="location__addr" for="location__addr"disabled >주소:</label>
       <input id="location__addr" value="${scheduleCard.placeAddress}" type="text" disabled />
       </div>
     <label class="memo" for="memo">메모</label>
     <textarea
       id="memo"
       name="story"
       rows="5"
       cols="33"
       disabled
       >${scheduleCard.placeContent}</textarea>
   </fieldset>`
    )
    .join('');
};

const makeEditScheduleCardNode = scheduleCards => {
  return scheduleCards
    .map(
      scheduleCard =>
        `<fieldset class="schedule-card">
     <div class="schedule-card__location">
     <i class="fa-solid fa-xmark"></i>
       <label class="location__name" for="location__name">장소명:</label>
       <input id="location__name" value="${scheduleCard.placeName}" type="text" />
       <label class="location__addr" for="location__addr" >주소:</label>
       <input id="location__addr" value="${scheduleCard.placeAddress}" type="text"  />
       </div>
     <label class="memo" for="memo">메모</label>
     <textarea
       id="memo"
       name="story"
       rows="5"
       cols="33"
       >${scheduleCard.placeContent}</textarea>
   </fieldset>`
    )
    .join('');
};

const makeDetailCardNode = dateCards => {
  console.log(makePositions(dateCards));
  return dateCards
    .map(
      dateCard =>
        `<details class="date-card">
      <summary>${dateCard.postDateCardTitle}</summary>
      <div class="schedule-card-container">
      ${makeScheduleCardNode(dateCard.postScheduleCard)}
        </div>
       </details>`
    )
    .join('');
};

const makeEditCardNode = dateCards => {
  console.log(makePositions(dateCards));
  return dateCards
    .map(
      dateCard =>
        `<details class="date-card">
      <summary>${dateCard.postDateCardTitle}<i class="fa-solid fa-xmark"></i></summary>
      <div class="schedule-card-container">
      ${makeEditScheduleCardNode(dateCard.postScheduleCard)}
        </div>
       </details>`
    )
    .join('');
};

const editPostRender = async (cardcontainer, id) => {
  if (window.localStorage.getItem('jwt') != null) {
    const { data } = await axios.get(`/api/post/detail/${id}`, {
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    });
    makeEditPost(data, cardcontainer);
    console.log('dsfa', data);
  }
};

const makeEditPost = (data, cardcontainer) => {
  console.log('dddd', data);
  document.querySelector('#editpost-title').value = data.postTitle;
  document.querySelector('#editpostplan-period-startdate').value = data.postStartDate.substring(0, 10);

  cardcontainer.innerHTML += makeEditCardNode(data.postDateCard);

  // if (data.postContent !== null) {
  //   document.querySelector('.review').innerHTML = `<span>후기</span>${data.postContent}`;
  // }
};

//detail page
const detailPostRender = async (cardcontainer, id, userid) => {
  if (window.localStorage.getItem('jwt') != null) {
    const { data } = await axios.get(`/api/post/detail/${id}`, {
      headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    });
    makeDetailPost(data, cardcontainer, id, userid);
  } else {
    const { data } = await axios.get(`/api/post/detail/${id}`);
    makeDetailPost(data, cardcontainer, id, userid);
  }
};

function makeDetailPost(data, cardcontainer, id, userid) {
  let isLiked = '';
  $.ajax({
    type: 'get',
    url: '/api/heart/get?postNo=' + id,
    headers: { Authorization: `Bearer ` + window.localStorage.getItem('jwt') },
    dataType: 'json',
    async: false,
    processData: false,
    success: function (data) {
      isLiked = data.result;
    },
  });

  if (data.memberId == userid) {
    document.querySelector('.detail-buttons').classList.remove('hidden');
  }
  if (isLiked) {
    document.querySelector('.fas.fa-heart').classList.add('liked');
  }

  document.querySelector('.detailpost').dataset.id = data.postNo;
  document.querySelector('.detailpost__title').textContent = data.postTitle;
  document.querySelector('.detailpost__user').textContent = `${data.memberNickname}(${data.memberId})`;
  document.querySelector('.post__likenum').textContent = data.heartCnt;
  document.querySelector('.detailpost__date').textContent = data.createdTime.substring(0, 10);
  document.querySelector('.date').textContent = `여행 기간 : ${data.postStartDate.substring(
    0,
    10
  )} ~ ${data.postEndDate.substring(0, 10)}`;
  document.querySelector('.cities').textContent = `여행 지역 : ${data.postRegionName}`;

  cardcontainer.innerHTML += makeDetailCardNode(data.postDateCard);

  if (data.postContent !== null) {
    document.querySelector('.review').innerHTML = `<span>후기</span>${data.postContent}`;
  }
}

const makecomment = (commentData, commentWriter, userId) => {
  let $buttons = '';
  if (userId === commentWriter) {
    $buttons = `    <div class="comment-buttons">
    <button class = "comment-editBtn">수정</button>
    <button class = "comment-editingBtn hidden">수정완료</button>
    <button class = "comment-deleteBtn">삭제</button>
    </div>`;
  }
  const comment = `<div class="post-comment" data-id=${commentData.commentNo}>
    <span class="comment-writer">${commentData.memberNickname}(${commentData.memberId})</span>
    <input class="comment-content" value=${commentData.commentContent} disabled>
    <span class="comment-date">${commentData.createdTime.substring(0, 10)}</span>
    ${$buttons}
    </div>`;
  return comment;
};

const commentRender = async (container, id, userId) => {
  const { data } = await axios.get(`/api/comment/${id}`);
  const comments = data.map(comment => makecomment(comment, comment.memberId, userId));
  container.innerHTML = comments.join('');
};

export default {
  renderTop3posts,
  addDataCard,
  addScheduleCard,
  detailPostRender,
  renderPosts,
  commentRender,
  editPostRender,
};
