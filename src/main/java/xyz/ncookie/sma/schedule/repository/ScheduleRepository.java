package xyz.ncookie.sma.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import xyz.ncookie.sma.global.repository.BaseRepository;
import xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentCountFlatDto;
import xyz.ncookie.sma.schedule.entity.Schedule;

public interface ScheduleRepository extends BaseRepository<Schedule, Long> {

    /**
     * 일정 정보, 작성자 정보, 댓글 개수 등의 데이터를 담고 있는 일정 목록 조회 쿼리
     * @param pageable size, page 등의 정보를 담고 있음
     * @return ScheduleWithFlatCommentDto 형태로 매핑 후, 응답 데이터에 사용할 때에는 ScheduleWithCommentResponseDto 로 변환 
     */
    @Query("""
                SELECT new xyz.ncookie.sma.schedule.dto.response.ScheduleWithCommentCountFlatDto(
                    s.id,
                    s.title,
                    s.contents,
                    m.id,
                    m.name,
                    COUNT(c.id),
                    s.createdAt,
                    s.modifiedAt
                )
                FROM Schedule s
                LEFT JOIN Member m ON s.member.id = m.id
                LEFT JOIN Comment c ON c.schedule.id = s.id
                GROUP BY
                    s.id
            """)
    Page<ScheduleWithCommentCountFlatDto> findAllWithCommentCount(Pageable pageable);

}
