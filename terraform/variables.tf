variable "aws_region" {
  description = "AWS Region"
  type        = string
}


variable "security_group_name" {
  type = string
}

variable "allowed_ports" {
  type = list(number)
}


variable "instance_name" {
  type = string
}

variable "instance_type" {
  type = string
}


variable "key_name" {
  type = string
}