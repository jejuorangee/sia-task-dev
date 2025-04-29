const processBtn = document.getElementById("processBtn");

processBtn.addEventListener('click', function() {
  console.log("버튼 클릭됨");
  // 체크된 체크박스 value 모으기
  const checkedBoxes = Array.from(
    document.querySelectorAll("input[name='originalFileName']:checked")
  ).map(checkBox => checkBox.value)
  
  // 확인용 로그
  console.log(checkedBoxes);
  console.log(checkedBoxes.length);

  // 선택된 체크박스가 없다면
  if(checkedBoxes.lenth === 0){
    alert("하나 이상의 파일을 선택해주세요 .")
    return;
  }

  let url;
  let jsondata;
  if(checkedBoxes.length === 1){
    // 단건 요청
    url = "/file/process/one";
    jsondata = {originalFileName : checkedBoxes[0]};
    console.log(jsondata);
  }
  else {
    // 다건 요청
    url = "/file/process/multiple";
    jsondata = checkedBoxes.map(originalFileName => ({ originalFileName }));
    console.log(jsondata);
  }
  // 확인용 로그
  // const jsondata = JSON.stringify(checkedBoxes);
  // console.log(jsondata);
  // console.log("url : "+url);
  // console.log("jsondata : "+jsondata);

  fetch(url,{
    method:'POST',
    headers:{'Content-Type':'application/json'},
    body:JSON.stringify(jsondata)
  })
  .then(response => {
    if(!response.ok){
      console.log(response);
      alert("오류가 발생했습니다 . 메인페이지로 이동합니다 .");
      window.location.href = "/";
      throw new Error(response.status);
    }
    alert("파일 처리가 완료되었습니다")
    window.location.href = "/file/processed";
  })
  .catch(err => {
    console.error(err);
    alert("오류가 발생했습니다 . 메인 페이지로 이동합니다 .");
    window.location.href = "/";
  });
});

