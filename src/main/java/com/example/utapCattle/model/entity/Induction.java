package com.example.utapCattle.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "induction", schema = "public")
public class Induction {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "eid")
	private String eid;

	@Column(name = "eartag")
	private String earTag;

	@Column(name = "chipnumber")
	private String chipNumber;

	@Column(name = "condition")
	private String condition;

	@Override
	public String toString() {
		return "Induction{" + "id=" + id + ", barcode='" + barcode + '\'' + ", eid='" + eid + '\'' + ", earTag='"
				+ earTag + '\'' + ", chipNumber='" + chipNumber + '\'' + ", condition='" + condition + '\'' + '}';
	}
}