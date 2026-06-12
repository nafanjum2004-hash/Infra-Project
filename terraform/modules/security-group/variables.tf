variable "security_group_name" {
  description = "Security Group Name"
  type        = string
}

variable "allowed_ports" {
  description = "Ports to allow"
  type        = list(number)
}