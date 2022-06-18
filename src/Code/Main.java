package Code;

import processing.core.*;

import javax.swing.*;

public class Main  extends PApplet{
    public static void main(String[] args) {

        PApplet.main(new String[]{Code.Main.class.getName()});
    }

    int filas = 25;
    int columnas = 25;
    int bs = 20;
    boolean greenBox = true;
    boolean purpleBox = true;

    boolean map[][] = new boolean[filas][columnas];
    PVector direction = new PVector(1, 0);
    Apple apple = new Apple();
    Snake humanSnake = new Snake(100, 200, 100, new PVector(2, 2), new PVector(2, 1));

    @Override
    public  void settings(){
        size(500,540);

    }
    @Override
    public  void setup(){
        frameRate(25);
        initGame();
    }
    @Override
    public  void draw(){
        background(25);
        drawMap();
        drawApple();
        playHumanSnake();
    }
     void initGame(){
        updateMAp();
        apple.spawn(map);
     }

     void updateMAp(){
        for (int i = 0;i < filas; i++){
            for (int  j = 0; j < columnas; j++){
                map[i][j] = true;
            }
         }



            //pOSICIONES SNAKE HUMAN
         for (int i = 1; i < humanSnake.posX.size(); i++) {
             map[humanSnake.posY.get(i)][humanSnake.posX.get(i)] = false;
         }
     }
    void drawMap(){
        fill(100,100,100);
        rect(0,500, width,40);

        //Marcador
        fill(100,200,100);
        rect(30,510,210,20);

        fill(100,100,200);
        rect(270,510,210,20);

        if (greenBox == false) {
            fill(250, 50, 50);
            rect(30, 510, 210, 20);
        }
        if (purpleBox == false) {
            fill(250, 50, 50);
            rect(270, 510, 210, 20);
        }
    }
    void drawApple(){
        fill(215,0,75);
        rect(apple.position.x * bs, apple.position.y * bs, bs,bs);
    }

    void playHumanSnake(){
        if (humanSnake.alive == true ){
        moveHumanSnake();
        drawSnake(humanSnake);
        detectBorder(humanSnake);
        }
    }

    void moveHumanSnake(){
        humanSnake.mover((int) direction.x, (int) direction.y);
        eat(humanSnake);
    }

    void eat(Snake snake) {
        if (snake.posX.get(0) == apple.position.x && snake.posY.get(0) == apple.position.y) {
            apple.spawn(map);
            snake.comer();
        }
    }
    void drawSnake(Snake snake){
        fill(snake.r, snake.g, snake.b);
        for (int i = 0; i < snake.posX.size(); i++) {
            rect(snake.posX.get(i) * bs, snake.posY.get(i) * bs, bs, bs);
        }
    }
    void restartGame() {
        //Comienza la partida de 0
        humanSnake.restart();
        initGame();
    }
    void detectBorder(Snake s) {

        //Detecta si se sale del mapa la cabeza de la serpiente
        if (s.posX.get(0) < 0 || s.posX.get(0) > (columnas - 1) || s.posY.get(0) < 0 || s.posY.get(0) > (filas - 1)) {
    s.restart();
        /*    Object[] options = { "Cancelar", "Intentar de Nuevo" };
            int option = JOptionPane.showOptionDialog(null, "Opps has Perdido \n Intentar de nuevo?", "Game Over",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (option) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                  s.restart();
                  break; }*/





        }
    }
    @Override
    public void keyPressed() {
        if (key == 'w' || keyCode == UP) {
            if (direction.y != 1) {
                direction.set(0, -1);
            }
        }
        if (key == 's' || keyCode == DOWN) {
            if (direction.y != -1) {
                direction.set(0, 1);
            }
        }
        if (key == 'a' || keyCode == LEFT) {
            if (direction.x != 1) {
                direction.set(-1, 0);
            }
        }
        if (key == 'd' || keyCode == RIGHT) {
            if (direction.x != -1) {
                direction.set(1, 0);
            }
        }
        //Reinicia el juego
        if (key == ' ') {
            restartGame();
        }
    }
    void deleteSanke(Snake s) {
        s.posX.clear();
        s.posY.clear();
        s.alive = false;
    }

    @Override
    public  void mouseClicked(){
        if (mouseX >= 30 && mouseX <= 240 && mouseY >= 510 && mouseY <= 530){
            greenBox = !greenBox;
            if (humanSnake.alive == true) {
                deleteSanke(humanSnake);
            } else {
                humanSnake.restart();
            }
        }
        if (mouseX >= 270 && mouseX <= 460 && mouseY >= 510 && mouseY <= 530){
            purpleBox = !purpleBox;

        }

    }


}
