
struct ClientInfoStruct{
 1: i64 id
 2: string ip
 3: i32 port
}


struct SCRequestSerInfo{
  1: i64 id
  2: map<ClientInfoStruct, string> serInfo
}
