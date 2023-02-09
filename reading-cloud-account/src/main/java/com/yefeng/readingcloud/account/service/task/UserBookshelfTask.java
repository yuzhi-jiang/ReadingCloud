package com.yefeng.readingcloud.account.service.task;

import com.yefeng.readingcloud.account.dao.UserBookshelfMapper;
import com.yefeng.readingcloud.common.pojo.account.UserBookshelf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 书架同步任务
 * @author: yefeng
 * @since: 2020/4/15
 */
public class UserBookshelfTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBookshelfTask.class);
    /** 处理类型：1.新增 2.修改 3.删除 */
    private Integer syncType;
    private UserBookshelf bookshelf;
    private UserBookshelfMapper bookshelfMapper;
    private Integer userId;

    @Override
    public void run() {
        try{
            if (1 == syncType) {
                this.bookshelf.setUserId(this.userId);
                this.bookshelfMapper.insert(this.bookshelf);
            } else if (2 == syncType) {
                this.bookshelf.setUserId(this.userId);
                this.bookshelfMapper.updateByUserIdAndBookId(this.bookshelf);
            } else if (3 == syncType) {
                if (this.bookshelf.getId()!=null){
                    this.bookshelfMapper.deleteById(this.bookshelf.getId());
                    System.out.println("根据id删除 bookID:"+this.bookshelf.getBookId()+" id:"+this.bookshelf.getId());
                }else{
                    System.out.println("根据bookid删除 bookId:"+this.bookshelf.getBookId()+" userId:"+userId);
                    this.bookshelfMapper.deleteByBookId(this.bookshelf.getBookId(),this.userId);
                }
            }
        } catch (Exception ex){
            LOGGER.error("书架同步失败，同步类型[{}]，异常:{}", this.syncType, ex);
        }
    }

    public UserBookshelfTask(){}

    public UserBookshelfTask(Integer syncType, UserBookshelf bookshelf, UserBookshelfMapper bookshelfMapper, Integer userId) {
        this.syncType = syncType;
        this.bookshelf = bookshelf;
        this.bookshelfMapper = bookshelfMapper;
        this.userId = userId;
    }
}
