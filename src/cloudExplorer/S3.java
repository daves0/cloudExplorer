package cloudExplorer;

public class S3 {
    /*
     * Cloud Explorer Copyright (C) December 20, 2013 By Phillip Tribble. This program
     * is free software; you can redistribute it and/or modify it under the terms of
     * the GNU General Public License as published by the Free Software Foundation;
     * either version 3 of the License, or (at your option) any later version. This
     * program is distributed in the hope that it will be useful, but WITHOUT ANY
     * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
     * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
     * You should have received a copy of the GNU General Public License along with
     * this program; if not, write to the Free Softw4are Foundation, Inc., 59 Temple
     * Place, Suite 330, Boston, MA 02111-1307 USA
     **/

    public static void helpargs() {
        System.out.print("\n[Cloud Explorer - CLI arguments.]\n"
                + "\nput filename bucket"
                + "\nget filename bucket"
                + "\ndelete filename bucket"
                + "\nls bucket"
                + "\nlistbuckets"
                + "\nrmbucket bucket"
                + "\nsynctos3 location bucket"
                + "\nsyncfroms3 destination bucket"
                + "\nuploadtest bucket size threads operations"
                + "\ndownloadtest bucket size threads operations"
                + "\n\n\n");
    }

    public static void main(String[] args) {
        int stop = 0;

        if (args.length > 0) {
            if (args[0].contains("daemon")) {
                Daemon daemon = new Daemon();
                daemon.gui = false;
                daemon.start();
                stop = 1;
            } else if (args[0].contains("listbuckets")) {
                CLI cli = new CLI();
                cli.start(args[0], null, null, null, null);
                stop = 1;
            }

            if (stop == 0) {
                if (args.length >= 2) {

                    if (args[0].contains("build")) {
                        Build build = new Build();
                        build.start(args[1], args[2], args[3]);
                    } else {
                        CLI cli = new CLI();
                        if (args.length < 3) {
                            cli.start(args[0], args[1], null, null, null);
                        }
                        if (args.length == 3) {
                            cli.start(args[0], args[1], args[2], null, null);
                        }
                        if (args.length == 5) {
                            cli.start(args[0], args[1], args[2], args[3], args[4]);
                        }
                    }

                } else {
                    System.out.print("\nError: not enough arguments used.\n\n\n");
                    helpargs();
                }
            }

        } else {
            try {
                NewJFrame gui = new NewJFrame();
                gui.main(args);
            } catch (Exception S3) {
            }
        }
    }
}
