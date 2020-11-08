ENABLE_ASLR:=sudo sysctl -w kernel.randomize_va_space=2
DISABLE_ASLR:=sudo sysctl -w kernel.randomize_va_space=0

BINARY_NAME:=caff_parser
FILE_NAME:=main.cpp

default: 
	g++ -o $(BINARY_NAME) $(FILE_NAME) -Wl,-z,relro,-z,now -fstack-protector-all -D_FORTIFY_SOURCE=2 -pie -z noexecstack

noProtection:
	g++ -o $(BINARY_NAME) $(FILE_NAME)

withASLR: 
	g++ -o $(BINARY_NAME) $(FILE_NAME) -Wl,-z,relro,-z,now -fstack-protector-all -D_FORTIFY_SOURCE=2 -pie -z noexecstack 
	$(ENABLE_ASLR)

noASLR: 
	g++ -o $(BINARY_NAME) $(FILE_NAME) -Wl,-z,relro,-z,now -fstack-protector-all -D_FORTIFY_SOURCE=2 -pie -z noexecstack 
	$(DISABLE_ASLR)