def getExtension(name:String):String = {
var ret = ""
for(i<- name.lastIndexOf('.')+1 to name.length-1 ){
ret += name.charAt(i);
}
return ret;
}
