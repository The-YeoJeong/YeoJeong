import write from '../html/write.html';

const writeNode = () => {
  const node = document.createElement('div');
  node.innerHTML = write;
  // Event

  $(function () {
    $('#summernote').summernote({
      height: 300,
      toolbar: [
        ['font', ['underline', 'clear']],
        ['fontname', ['fontname']],
        ['color', ['color']],
        ['para', ['paragraph']],
        ['height', ['height']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'hr']],
        ['view', ['codeview']],
        ['help', ['help']],
      ],
    });
  });

  const $cardContainer = node.querySelector('.card-container');

  node.querySelector('.add-date-card-button').addEventListener('click', () => {
    $cardContainer.insertAdjacentHTML(
      'beforeend',
      `<details class="date-card">
      <summary><input class="data-card__title" placeholder="1일차" /><i class="fa-solid fa-xmark"></i></summary>
      <div class="schedule-card-container">
        <fieldset class="schedule-card">
          <div class="schedule-card__location">
            <i class="fa-solid fa-xmark"></i>
            <label class="location__name" for="location__name">장소명</label>
            <input id="location__name" type="text" />
            <label class="location__addr" for="location__addr">주소</label>
            <input id="location__addr" type="text" disabled />
            <i class="fas fa-magnifying-glass"></i>
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
        </fieldset>
      </div>
      <div class="date-card__buttons">
        <button class="add-schedule-button">+</button>
      </div>
    </details>`
    );
  });

  $cardContainer.addEventListener('click', e => {
    if (e.target.className === 'add-schedule-button') {
      const { previousElementSibling } = e.target.parentNode;
      previousElementSibling.insertAdjacentHTML(
        'beforeend',
        `<fieldset class="schedule-card">
            <div class="schedule-card__location">
              <i class="fa-solid fa-xmark"></i>
              <label class="location__name" for="location__name">장소명</label>
              <input id="location__name" type="text" />
              <label class="location__addr" for="location__addr">주소</label>
              <input id="location__addr" type="text" disabled />
              <i class="fas fa-magnifying-glass"></i>
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
    }
    if (e.target.className === 'schedule-card__button') {
      if (e.target.parentNode.children[2].disabled) {
        e.target.parentNode.children[0].children[2].disabled = false;
        e.target.parentNode.children[2].disabled = false;
        e.target.textContent = '저장';
      } else {
        e.target.parentNode.children[0].children[2].disabled = true;
        e.target.parentNode.children[2].disabled = true;
        e.target.textContent = '수정';
      }
    }
    if (e.target.classList.contains('fa-xmark')) {
      e.target.parentNode.parentNode.remove();
    }
    if (e.target.className === 'saveAll-button') {
      e.target.parentNode.parentNode.getElementsByTagName('input').map(input => {
        input.disabled = true;
      });
    }
  });

  node.querySelector('.review-button').addEventListener('click', e => {
    if (e.target.textContent === '후기 작성 폼 열기') e.target.textContent = '후기 작성 폼 닫기';
    else e.target.textContent = '후기 작성 폼 열기';

    document.querySelector('.review-container').classList.toggle('hidden');
  });

  return node.children;
};

export default writeNode;
