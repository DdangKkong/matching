### 변경사항
<!-- 이 PR에서 어떤점들이 변경되었는지 기술해주세요. 가급적이면 as-is, to-be를 활용해서 작성해주세요.  -->

feat :  수정 및 업데이트

- 상세 내용 : 
  - 모든 dto @NotNull, @NotBlank 추가 및 service 에 @Transactional 추가
  - Controller 에서 Request 받는 형태 일원화 및 GetMapping, DeleteMapping 시 parameter 로 데이터 받아오도록 수정
  - URL 연관성 기준으로 정리
  - CustomException 적용하여 Errorcode 통합 관리
 
refactor :


**AS-IS**

수정 및 업데이트

**TO-BE**

미흡한 부분 확인 되는대로 추가할 예정.

### 테스트
<!-- 본 변경사항이 테스트가 되었는지 기술해주세요 --> 
- [ ] 테스트 코드

- [X] API 테스트