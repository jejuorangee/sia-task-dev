// const form = document.getElementById("downloadFileForm");
// const formData = new FormData(form);
// // FormData.getAll('animal') 로 배열로 뽑아낼 수 있음
// const values = formData.getAll("name"); // ['cat','dog']
// fetch('/file/download', {
//   method: 'POST',
//   body: formData
// });

document.getElementById("transCOGNupload").addEventListener('click', function() {
  const form = document.getElementById("downloadFileForm");
  // 체크되지 않은 checkbox를 disabled 처리
  form.querySelectorAll("input[type='checkbox'][name='name']").forEach(cb => {
    if (!cb.checked) {
      cb.disabled = true;
    }
  });
  // 폼 전송
  form.submit();
});