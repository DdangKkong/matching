### 변경사항
<!-- 이 PR에서 어떤점들이 변경되었는지 기술해주세요. 가급적이면 as-is, to-be를 활용해서 작성해주세요.  -->

feat :  Comment(댓글) 기능 구현 (CRUD, 대댓글)

대댓글 기능 (parentId, level) : 대댓글을 작성하려는 댓글의 commentId 를 parentId 로 저장하여 기록한다.
                              대댓글을 작성하려는 댓글의 level 보다 대댓글의 level 이 1 만큼 더 높다.

위의 대댓글 기능 (parentId, level) 을 통해 어디에, 몇번째로 달린 대댓글인지를 확인 할 수 있다.

refactor : Project(구인 게시글) 에서 미처 수정하지 못한 부분 수정 (불필요한 @NotNull 제거, Mapper 제거)

**AS-IS**

Comment(댓글) 기능 구현 완료

database 올바르게 작동하는지 확인 완료

**TO-BE**

Application(지원서) 기능 구현 예정

### 테스트
<!-- 본 변경사항이 테스트가 되었는지 기술해주세요 --> 
- [ ] 테스트 코드

- [X] API 테스트