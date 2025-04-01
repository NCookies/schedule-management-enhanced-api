package xyz.ncookie.sma.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.ncookie.sma.global.exception.BusinessException;
import xyz.ncookie.sma.global.exception.ErrorCode;

/**
 * 여러 도메인의 Repository에서 공통적으로 사용하는 기능을 default 메서드로 구현한 인터페이스
 * @param <T>   Entity 타입
 * @param <ID>  Entity ID 타입
 */
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default T findByIdOrElseThrow(ID id) {
        return findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND, "해당 Entity를 찾을 수 없습니다. id = " + id)
        );
    }

}
