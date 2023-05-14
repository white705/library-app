package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.user.User;
        import com.group.libraryapp.domain.user.UserRepository;
        import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
        import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
        import com.group.libraryapp.dto.book.BookCreateRequest;
        import com.group.libraryapp.dto.book.BookLoanRequest;
        import com.group.libraryapp.dto.book.BookReturnRequest;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.IllformedLocaleException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;

    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        //1.책 정보를 가져온다.대출기록 정보 확인해서 대출중인지 확인
        //2.대출기록 정보를 확인해서 대출중인지 확인합니다.
        //3.만약에 확인했는데 대출중이라면 예외발생

        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false))
            throw new IllegalArgumentException("대출되어 있는 책입니다 ㅠㅠㅠㅠㅠ");

        //4.유저 정보가져오기.
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        //5.대출정보 저장하기
//        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        user.loanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);
//
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(IllformedLocaleException::new);
//
//        history.doReturn();
        user.returnBook(request.getBookName());
    }
}
