#version 330 core

out vec4 fragment_color;

in vec2 v_TexCoord;

uniform int OBJECT;

uniform sampler2D t_board;
uniform sampler2D t_pawn;
uniform sampler2D t_background;
uniform sampler2D t_text;
uniform sampler2D t_menu;

void main(){
    if(OBJECT == 0)
        fragment_color = texture(t_board, v_TexCoord);
    if(OBJECT == 1)
        fragment_color = texture(t_pawn, v_TexCoord);
    if(OBJECT == 2)
        fragment_color = texture(t_background, v_TexCoord);
    if(OBJECT == 4)
         fragment_color = texture(t_text, v_TexCoord);
    if(OBJECT == 5)
        fragment_color = texture(t_menu, v_TexCoord);
}