package com.tyut.accesscontrol.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class SignFromDTO implements Serializable {

	private MultipartFile file;

	private Long userId;

	private static final long serialVersionUID = 1L;

}
