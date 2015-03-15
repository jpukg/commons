package com.samenea.commons.component.log.hibernateappender.model;

/**
 * @author: Amar
 * Date: Dec 13, 2008
 */
import javax.persistence.*;

@Embeddable
public class LogEventThrowable {
    @Column
    private Integer position;
    @Column(length = 1000)
    private String message;

    public LogEventThrowable(int position, String throwableMessage) {
        setPosition(position);
        setMessage(throwableMessage);
    }

    public LogEventThrowable() {
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEventThrowable that = (LogEventThrowable) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (position != null ? position.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "LogEventThrowable{" +
                "position=" + position +
                ", message='" + message + '\'' +
                '}';
    }
}
