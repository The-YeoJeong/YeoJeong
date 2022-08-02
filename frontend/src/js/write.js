import write from '../html/write.html';
import map from './map';

const writeNode = () => {
  const node = document.createElement('div');

  node.innerHTML = write;
  // Event

  $(function () {
    $('#summernote').summernote({
      height: 300,
      minHeight: null,
      maxHeight: null,
      focus: true,
      callbacks: {
        onImageUpload: function (files, editor, welEditable) {
          for (var i = 0; i < files.length; i++) {
            sendFile(files[i], this);
          }
        },
      },
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
    $('#plan-period-startdate').datepicker({
      dateFormat: 'yy-mm-dd',
      // 날짜의 형식
      changeMonth: true,
      // 월을 이동하기 위한 선택상자 표시여부
      minDate: 0,
      // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
      onClose: function (selectedDate) {
        // 시작일(fromDate) datepicker가 닫힐때
        // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
        $('#plan-period-enddate').datepicker('option', 'minDate', selectedDate);
      },
    });
    //종료일
    $('#plan-period-enddate').datepicker({
      dateFormat: 'yy-mm-dd',
      changeMonth: true,
      minDate: 0,
      // 오늘 이전 날짜 선택 불가
      onClose: function (selectedDate) {
        // 종료일(toDate) datepicker가 닫힐때
        // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
        $('#plan-period-startdate').datepicker('option', 'maxDate', selectedDate);
      },
    });
  });

  function sendFile(file, el) {
    var form_data = new FormData();
    form_data.append('file', file);
    $.ajax({
      data: form_data,
      type: 'POST',
      url: '/api/image',
      cache: false,
      contentType: false,
      enctype: 'multipart/form-data',
      processData: false,
      success: function (url) {
        $(el).summernote('insertImage', url, function ($image) {
          $image.css('width', '50%');
        });
      },
    });
  }

  function show() {
    document.querySelector('.background').className = 'background show';
  }
  
  node.querySelector('.searchLoca').addEventListener('click', () => {
    map.searchPlaces();
  });

  node.querySelector('#close').addEventListener('click', close);

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
    if (e.target.id === 'location__addr') {
      map.setInputArea(e.target, e.target.previousElementSibling.previousElementSibling);
      show();
    }
  });

  let mapContainer = node.querySelector('.map'), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
      level: 3, // 지도의 확대 레벨
    };

  map.makeMap(mapContainer, mapOption);

  node.querySelector('.review-button').addEventListener('click', e => {
    if (e.target.textContent === '후기 작성 폼 열기') e.target.textContent = '후기 작성 폼 닫기';
    else e.target.textContent = '후기 작성 폼 열기';

    document.querySelector('.review-container').classList.toggle('hidden');
  });

  return node.children;
};

export default writeNode;
