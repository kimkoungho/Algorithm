package programmers.hash;


import java.util.*;
import java.util.stream.*;

// https://programmers.co.kr/learn/courses/30/lessons/42579
// 베스트 앨럼
public class BestAlbum {

    static class Song implements Comparable<Song> {
        private int index;
        private String genre;
        private int play;

        public Song(int index, String genre, int play) {
            this.index = index;
            this.genre = genre;
            this.play = play;
        }

        public int getIndex() {
            return index;
        }

        public String getGenre() {
            return genre;
        }

        public int getPlay() {
            return play;
        }

        @Override
        public String toString() {
            return "Song{" +
                    "index=" + index +
                    ", genre='" + genre + '\'' +
                    ", play=" + play +
                    '}';
        }

        // natural order : play 가 작은것 우선 + 같으면 인덱스로 큰게 앞으로 <- 이렇게 구현해서 버릴것을 탐색 한다
        // reverse order : play 가 큰 것 우선 + 같은면 인젝스가 작언게 앞으로
        @Override
        public int compareTo(Song other) {
            if (this.play == other.getPlay()) {
                return this.index > other.getIndex() ? -1 : 1;
            }
            return this.play < other.getPlay() ? -1 : 1;
        }
    }
    static class Genre {
        private long sum = 0;
        private PriorityQueue<Song> songs = new PriorityQueue<Song>();

        public void addSong(Song song) {
            sum += song.getPlay();
            songs.add(song);

            // 3 개 이상일 경우 가장 작은 것을 버린다
            if (songs.size() > 2) {
                songs.poll();
            }
        }

        public long getSum() {
            return sum;
        }

        public List<Integer> getTopSongIds() {
            return songs.stream()
                    .sorted(Comparator.reverseOrder()) // 내림차순으로 변경
                    .collect(Collectors.toList())
                    .stream().map(Song::getIndex).collect(Collectors.toList());
        }
    }

    static class Solution {
        /** i = 노래의 key
         * i : 1 ~ 10,000
         * @param genres : 노래의 장르
         * @param plays : 노래가 재생된 횟수
         * @return
         * - 가장 많이 재생된 장르 > 장르내 많이 재생된 노래 > 장르내 재생 횟수가 같으면 번호 낮은것 부터
         */
        public int[] solution(String[] genres, int[] plays) {
            // 장르 별로 count sum 해서 장르별 정렬
            // 각 장르별 가장 큰 2곡 찾기
            // 1차원 배열에 순서대로 반환

            // 장르별로 묶는다
            Map<String, Genre> genreMap = new HashMap<String, Genre>();

            // key: 장르 이름
            // value : 최상위 2개
            for (int i = 0; i < genres.length; i++) {
                Song song = new Song(i, genres[i], plays[i]);

                Genre genre = genreMap.getOrDefault(song.genre, new Genre());
                genre.addSong(song);

                genreMap.put(genres[i], genre);
            }


            List<Genre> genreList = genreMap.entrySet().stream()
                    .map(entry -> entry.getValue())
                    // 장르별 1차 정렬
                    .sorted(Comparator.comparing(Genre::getSum).reversed())
                    .collect(Collectors.toList());

            List<Integer> answerList = genreList.stream()
                    .flatMap(genre -> genre.getTopSongIds().stream())
                    .collect(Collectors.toList());

            int[] answer = new int[answerList.size()];
            for(int i=0; i<answerList.size(); i++){
                answer[i] = answerList.get(i);
            }

            return answer;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] answer1 = solution.solution(
                new String[]{"classic", "pop", "classic", "classic", "pop"},
                new int[]{500, 600, 150, 800, 2500}
        );

        System.out.println(Arrays.toString(answer1));
        // [4, 1, 3, 0]

        // 장르내 카운트가 같은게 있는 경우
        // 같지만 2개가 가장 크다
        int[] answer2 = solution.solution(
                new String[]{"classic", "pop", "classic", "classic", "classic"},
                new int[]{500, 600, 150, 800, 800}
        );
        System.out.println(Arrays.toString(answer2));
        // [3, 4, 1]


        // 3개 인 경우
        int[] answer3 = solution.solution(
                new String[]{"classic", "pop", "classic", "classic"},
                new int[]{500, 600, 150, 800}
        );
        System.out.println(Arrays.toString(answer3));
        // [3, 0, 1]


    }
}
