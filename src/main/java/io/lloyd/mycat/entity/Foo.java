package io.lloyd.mycat.entity;

import java.sql.Date;
import java.util.Objects;

import com.google.common.base.MoreObjects;

/**
 * 一个模拟实体<br/>
 * Created by LilyBlooper(lilyblooper@163.com) on 2018/5/31.
 */
public class Foo {
	private long id;
	private long uid;
	private String text;
	private Date date;

	public Foo(long id, long uid, String text, Date date) {
		this.id = id;
		this.uid = uid;
		this.text = text;
		this.date = date;
	}

	public Foo(long uid, String text) {
		this.uid = uid;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Foo foo = (Foo) o;
		return id == foo.id && uid == foo.uid && Objects.equals(text, foo.text)
				&& Objects.equals(date, foo.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uid, text, date);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("uid", uid).add("text", text)
				.add("date", date).toString();
	}
}
