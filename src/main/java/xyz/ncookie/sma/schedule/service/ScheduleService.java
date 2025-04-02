package xyz.ncookie.sma.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ncookie.sma.comment.entity.Comment;
import xyz.ncookie.sma.comment.repository.CommentRepository;
import xyz.ncookie.sma.common.exception.BusinessException;
import xyz.ncookie.sma.common.exception.ErrorCode;
import xyz.ncookie.sma.member.entity.Member;
import xyz.ncookie.sma.member.repository.MemberRepository;
import xyz.ncookie.sma.schedule.dto.request.ScheduleSaveRequestDto;
import xyz.ncookie.sma.schedule.dto.request.ScheduleUpdateRequestDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleResponseDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentCountResponseDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentCountFlatDto;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentsResponseDto;
import xyz.ncookie.sma.schedule.entity.Schedule;
import xyz.ncookie.sma.schedule.repository.ScheduleRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    /**
     * 일정 생성
     * @param memberId 회원 ID
     * @param dto 일정 생성에 필요한 정보들을 담고 있음
     * @return 저장된 일정 정보
     */
    @Transactional
    public ScheduleResponseDto saveSchedule(Long memberId, ScheduleSaveRequestDto dto) {

        Member member = memberRepository.findByIdOrElseThrow(memberId);

        Schedule savedSchedule = scheduleRepository.save(
                Schedule.of(
                        member,
                        dto.title(),
                        dto.contents()
                )
        );

        return ScheduleResponseDto.fromEntity(savedSchedule);
    }

    /**
     * 일정 단일 조회 (댓글 포함)
     * @param scheduleId 일정 ID
     * @return 일정에 달린 댓글들을 포함한 일정 정보
     */
    @Transactional(readOnly = true)
    public ScheduleWithCommentsResponseDto findScheduleWithComments(Long scheduleId) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Sort commentSort = Sort.by("createdAt").descending();
        Page<Comment> pageComments = commentRepository.findBySchedule_id(PageRequest.of(0, 10, commentSort), scheduleId);

        return ScheduleWithCommentsResponseDto.fromEntity(findSchedule, pageComments);
    }

    /**
     * 전체 일정 조회
     * @param pageable 페이징 객체 생성을 위한 page, size 등의 정보를 담고 있음
     * @return 회원 정보, 댓글 개수 등을 포함한 일정 리스트
     */
    @Transactional(readOnly = true)
    public Page<ScheduleWithCommentCountResponseDto> findAllSchedules(Pageable pageable) {

        Page<ScheduleWithCommentCountFlatDto> findAllComment = scheduleRepository.findAllWithCommentCount(pageable);

        return findAllComment.map(ScheduleWithCommentCountFlatDto::toResponseDto);
    }

    /**
     * 일정 수정. 일정 ID와 회원 ID가 일치하지 않으면 예외가 발생한다.
     * @param scheduleId 일정 ID
     * @param memberId 회원 ID
     * @param dto 일정 수정할 내용
     * @return 수정된 일정 정보
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, Long memberId, ScheduleUpdateRequestDto dto) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        verifyScheduleOwner(findSchedule, memberId);

        findSchedule.updateSchedule(dto.title(), dto.contents());

        // 영속성 컨텍스트가 dirty checking 하므로 따로 save()를 호출하지 않아도 됨
        return ScheduleResponseDto.fromEntity(findSchedule);
    }

    /**
     * 일정 삭제. 일정 ID와 회원 ID가 일치하지 않으면 예외가 발생한다.
     * @param scheduleId 일정 ID
     * @param memberId 회원 ID
     * 반환 데이터는 따로 없음
     */
    @Transactional
    public void deleteScheduleById(Long scheduleId, Long memberId) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        verifyScheduleOwner(findSchedule, memberId);

        scheduleRepository.delete(findSchedule);
    }

    /**
     * 일정 수정 또는 삭제 시 해당 일정을 작성한 회원인지 검증한다.
     * @param findSchedule 일정 ID를 통해 조회한 일정 객체
     * @param sessionMemberId 요청을 날린 사용자의 회원 ID
     */
    private void verifyScheduleOwner(Schedule findSchedule, Long sessionMemberId) {

        Long findMemberId = findSchedule.getMember().getId();
        if (!Objects.equals(sessionMemberId, findMemberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_OPERATION, "본인이 작성한 일정만 수정 또는 삭제할 수 있습니다. 일정 ID, 회원 ID: " + findSchedule.getId() + ", " + findMemberId);
        }
    }

}
